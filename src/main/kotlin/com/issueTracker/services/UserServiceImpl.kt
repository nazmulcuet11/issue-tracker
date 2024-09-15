package com.issueTracker.services


import com.issueTracker.dtos.request.LoginRequest
import com.issueTracker.dtos.request.SignupRequest
import com.issueTracker.dtos.responses.LoginResponse
import com.issueTracker.dtos.responses.SignupResponse
import com.issueTracker.entities.User
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.UserService
import org.springframework.security.crypto.bcrypt.BCrypt

class UserServiceImpl(
    private val repository: UserRepository,
    private val jwtService: JwtService,
) : UserService {
    override suspend fun getAllUsers(): List<User> {
        return repository.selectAll()
    }

    override suspend fun getUserById(id: Int): User? {
        return repository.selectById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return repository.selectByEmail(email)
    }

    override suspend fun signup(request: SignupRequest): Result<SignupResponse> {
        if (repository.selectByEmail(request.email) != null) {
            return Result.failure(emailAlreadyExists)
        }

        val user = repository.insert(
            User(
                id = 0,
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                passwordHash = BCrypt.hashpw(request.password, BCrypt.gensalt())
            )
        ) ?: throw Error("could not create user")

        val accessToken = jwtService.generateAccessToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        val response = SignupResponse(
            user.id,
            accessToken,
            refreshToken
        )
        return Result.success(response)
    }

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        val user = repository.selectByEmail(request.email) ?: return Result.failure(noUserFoundWithEmail)
        if (!BCrypt.checkpw(request.password, user.passwordHash)) {
            return Result.failure(incorrectPassword)
        }
        val accessToken = jwtService.generateAccessToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        val response = LoginResponse(
            accessToken,
            refreshToken
        )
        return Result.success(response)
    }

    companion object Errors {
        val emailAlreadyExists = Error("email already registered")
        val noUserFoundWithEmail = Error("no user found with the email")
        val incorrectPassword = Error("incorrect password")
    }
}

