package nuricanozturk.dev.android.app.data.service.mapper.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nuricanozturk.dev.android.app.data.service.mapper.ILoginInfoMapper
import org.mapstruct.factory.Mappers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginInfoMapperModule
{
    @Provides
    @Singleton
    fun provideLoginInfoMapper() : ILoginInfoMapper =
        Mappers.getMapper(ILoginInfoMapper::class.java)
}