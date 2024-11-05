package dev.nazmul.issueTracker.authService.services

import dev.nazmul.issueTracker.authService.dtos.requests.AssignRoleRequest
import dev.nazmul.issueTracker.authService.dtos.requests.LoginRequest
import dev.nazmul.issueTracker.authService.dtos.requests.SignupRequest
import dev.nazmul.issueTracker.authService.dtos.requests.TokenRefreshRequest
import dev.nazmul.issueTracker.authService.dtos.responses.AuthResponse
import dev.nazmul.issueTracker.authService.dtos.responses.SignupResponse
import dev.nazmul.issueTracker.authService.models.User
import dev.nazmul.issueTracker.authService.repositories.interfaces.UserRepository
import dev.nazmul.issueTracker.authService.repositories.interfaces.UserTokenRepository
import dev.nazmul.issueTracker.authService.services.interfaces.JwtService
import dev.nazmul.issueTracker.authService.services.interfaces.UserService
import org.springframework.security.crypto.bcrypt.BCrypt

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val tokenRepository: UserTokenRepository,
    private val jwtService: JwtService,
) : UserService {
    override suspend fun getUsers(offset: Long, limit: Int): List<User> {
        return userRepository.findAll(offset, limit)
    }

    override suspend fun getUserById(id: Int): User? {
        return userRepository.findById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override suspend fun signup(request: SignupRequest): Result<SignupResponse> {
        if (userRepository.findByEmail(request.email) != null) {
            return Result.failure(emailAlreadyExists)
        }

        val passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt())
        val user = userRepository.createUser(
            request.firstName,
            request.lastName,
            request.email,
            passwordHash
        ) ?: throw Error("could not create user")

        val authResponse = generateAuthResponse(user)
        val response = SignupResponse(
            user.id,
            authResponse.accessToken,
            authResponse.refreshToken,
        )
        return Result.success(response)
    }

    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        val user = userRepository.findByEmail(request.email) ?: return Result.failure(noUserFoundWithEmail)
        if (!BCrypt.checkpw(request.password, user.passwordHash)) {
            return Result.failure(incorrectPassword)
        }
        val response = generateAuthResponse(user)
        return Result.success(response)
    }

    override suspend fun logout(userId: Int, token: String) {
        tokenRepository.delete(token, userId)
    }

    override suspend fun logoutAll(userId: Int) {
        tokenRepository.deleteAllToken(userId)
    }

    override suspend fun tokenRefresh(request: TokenRefreshRequest): Result<AuthResponse> {
        val decodedJWT = jwtService.verify(request.refreshToken)
        if (jwtService.audienceMatches(decodedJWT.audience).not()) {
            return Result.failure(invalidRefreshToken)
        }
        val id = decodedJWT.getClaim("id").asInt()

        if (tokenRepository.exists(request.refreshToken, id).not()) {
            return Result.failure(invalidRefreshToken)
        }
        val user = userRepository.findById(id) ?: return Result.failure(invalidRefreshToken)

        tokenRepository.delete(request.refreshToken, id)
        val response = generateAuthResponse(user)
        return Result.success(response)
    }

    override suspend fun assignRole(request: AssignRoleRequest) {
        userRepository.assignRole(request.userId, request.roleId)
    }

    private suspend fun generateAuthResponse(user: User): AuthResponse {
        val accessToken = jwtService.generateAccessToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        tokenRepository.insert(refreshToken, user.id)
        return AuthResponse(
            accessToken,
            refreshToken
        )
    }

    companion object Errors {
        val emailAlreadyExists = Error("email already registered")
        val noUserFoundWithEmail = Error("no user found with the email")
        val incorrectPassword = Error("incorrect password")
        val invalidRefreshToken = Error("invalid refresh token")
    }
}

