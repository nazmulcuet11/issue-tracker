package dev.nazmul.issueTracker.authService.di

import dev.nazmul.issueTracker.authService.constants.REQUEST_SCOPE_NAME
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.createApplicationPlugin
import io.ktor.util.AttributeKey
import java.util.*
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin

private val koinScopeAttributeKey = AttributeKey<Scope>("koinScope")

val KoinScope = createApplicationPlugin("KoinScope") {
    onCall { call ->
        val scope = getKoin().createScope(UUID.randomUUID().toString(), named(REQUEST_SCOPE_NAME))
        call.attributes.put(koinScopeAttributeKey, scope)
    }

    onCallRespond { call ->
        val scope = call.attributes[koinScopeAttributeKey]
        scope.close()
        call.attributes.remove(koinScopeAttributeKey)
    }
}

val ApplicationCall.koinScope: Scope get() = this.attributes[koinScopeAttributeKey]
