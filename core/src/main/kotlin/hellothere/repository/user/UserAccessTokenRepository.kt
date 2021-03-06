package hellothere.repository.user

import hellothere.model.user.UserAccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface UserAccessTokenRepository : JpaRepository<UserAccessToken, String> {
    fun findFirstByIdAndRefreshTokenNotNull(userId: String): UserAccessToken?
}
