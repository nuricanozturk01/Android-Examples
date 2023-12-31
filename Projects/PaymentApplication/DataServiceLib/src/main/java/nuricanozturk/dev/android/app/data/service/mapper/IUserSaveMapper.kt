package nuricanozturk.dev.android.app.data.service.mapper

import nuricanozturk.dev.android.app.data.service.dto.UserSaveDTO
import nuricanozturk.dev.android.repositorylib.entity.User
import org.mapstruct.Mapper

@Mapper(implementationName = "UserSaveMapperImpl")
interface IUserSaveMapper
{
    fun toUser(userSaveDTO : UserSaveDTO) : User
    fun toUserSaveDTO(user : User) : UserSaveDTO
}