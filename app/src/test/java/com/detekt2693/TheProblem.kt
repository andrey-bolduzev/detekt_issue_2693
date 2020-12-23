package com.detekt2693

import com.nhaarman.mockitokotlin2.stub
import org.mockito.internal.invocation.InterceptedInvocation
import org.mockito.invocation.InvocationOnMock
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.startCoroutineUninterceptedOrReturn

@Suppress("UNCHECKED_CAST")
fun <T> (suspend () -> T).badMethod(answer: suspend (InvocationOnMock) -> T?) {
    stub {
        onBlocking { this@badMethod() }.thenAnswer {
            //all suspend functions/lambdas has Continuation as the last argument.
            //InvocationOnMock does not see last argument
            val rawInvocation = it as InterceptedInvocation
            val continuation = rawInvocation.rawArguments.last() as Continuation<T?>

            answer.startCoroutineUninterceptedOrReturn(it, continuation)
        }
    }
}