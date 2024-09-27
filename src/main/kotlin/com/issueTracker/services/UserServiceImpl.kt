package com.issueTracker.services


import com.issueTracker.dtos.request.LoginRequest
import com.issueTracker.dtos.request.SignupRequest
import com.issueTracker.dtos.request.TokenRefreshRequest
import com.issueTracker.dtos.responses.AuthResponse
import com.issueTracker.dtos.responses.SignupResponse
import com.issueTracker.entities.User
import com.issueTracker.repositories.interfaces.RoleRepository
import com.issueTracker.repositories.interfaces.UserTokenRepository
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.UserService
import org.springframework.security.crypto.bcrypt.BCrypt

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tokenRepository: UserTokenRepository,
    private val jwtService: JwtService,
) : UserService {
    override suspend fun getAllUsers(): List<User> {
        val users = userRepository.selectAll()
        for (user in users) {
            user.roles = roleRepository.getRolesByUserId(user.id)
        }
        return users
    }

    override suspend fun getUserById(id: Int): User? {
        return userRepository.selectById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userRepository.selectByEmail(email)
    }

    override suspend fun signup(request: SignupRequest): Result<SignupResponse> {
        if (userRepository.selectByEmail(request.email) != null) {
            return Result.failure(emailAlreadyExists)
        }

        val user = userRepository.insert(
            User(
                id = 0,
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt()),
            )
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
        val user = userRepository.selectByEmail(request.email) ?: return Result.failure(noUserFoundWithEmail)
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
        val user = userRepository.selectById(id) ?: return Result.failure(invalidRefreshToken)

        tokenRepository.delete(request.refreshToken, id)
        val response = generateAuthResponse(user)
        return Result.success(response)
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

