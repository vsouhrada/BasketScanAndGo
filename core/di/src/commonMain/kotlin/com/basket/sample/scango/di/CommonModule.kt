import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {

    single { provideIoDispatcher() } withOptions {
        named(BasketDispatchers.IO)
    }

    single { provideDefaultDispatcher() } withOptions {
        named(BasketDispatchers.Default)
    }

    single {
        provideApplicationScope(
            get(named(BasketDispatchers.Default))
        )
    }

    single<CoroutineDispatcher> { provideIoDispatcher() }
}

expect fun provideIoDispatcher(): CoroutineDispatcher

fun provideDefaultDispatcher() = Dispatchers.Default

fun provideApplicationScope(dispatcher: CoroutineDispatcher): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcher)

enum class BasketDispatchers {
    IO, Default
}
