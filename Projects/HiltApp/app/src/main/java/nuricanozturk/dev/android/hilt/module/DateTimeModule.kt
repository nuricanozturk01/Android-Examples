package nuricanozturk.dev.android.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.time.LocalDateTime

@Module
@InstallIn(ActivityComponent::class) // scope boyu
object DateTimeModule
{
    @Provides
    @SystemDateTimeIntercepter
    fun createLocalDateTime() : LocalDateTime = LocalDateTime.now()
}