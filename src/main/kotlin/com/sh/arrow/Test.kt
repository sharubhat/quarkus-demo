package com.sh.arrow

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrElse

// ref: https://www.youtube.com/watch?v=eFheAErqJzA&list=PPSV
class Test {

    // function behavior Double, Double -> Double (or crash)
    // only way to know this is either reading the code or javadoc
    fun divide(a: Double, b: Double): Double = if (b == 0.0) {
        throw ArithmeticException("Can't divide by 0")
    } else {
        a / b
    }

    // using nullable
    // client misses error context - what went wrong
    fun divide2(a: Double, b: Double): Double? = if (b == 0.0) null else a / b

    // chaining for happy flow
    val result: Double = divide2(1.0, 0.0)
        ?.let { it * 2}
        ?.let { divide2(it, 2.0)}
        ?: 0.0

    // using Arrow's Either
    // Left of Either is always error context, Right of Either contains return value
    fun divideEither(a: Double, b: Double): Either<String, Double> = if (b == 0.0) {
        Either.Left("Can't divide by 0")
    } else {
        Either.Right(a / b)
    }

    // being lazy, you can use Either.catch where left is automatically Throwable
    fun divideEitherCatch(a: Double, b: Double): Either<Throwable, Double> =
        Either.catch { a / b }

    fun divideEitherMapLeft(a: Double, b: Double): Either<String, Double> =
        Either.catch { a / b }
            .mapLeft { "Can't divide by zero" }

    // map is mapRight
    fun divideEitherMapRight(a: Double, b: Double): Either<Throwable, String> =
        Either.catch { a / b }
            .map { rightValue -> rightValue.toString() }

    val myEither: Either<String, Double> = divideEither(10.0, 2.0)
    // Ignore left - could simply use Kotlin's nullability
    val orNull: Double? = myEither.getOrNull()
    val orElse: Double = myEither.getOrElse { 0.0 }

    // Or handle left with pattern match
    val x = when(myEither) {
        is Either.Left -> println(myEither.value)
        is Either.Right -> println(myEither.value * 2)
    }

    val orHandle: Double = myEither.getOrElse { println(it); 0.0 }

    // chaining happy flow with Arrow
    val result1: Double = divideEither(6.0, 3.0)      // Right(2.0)
        .map { right: Double -> right * 2 }                 // Right(4.0)
        .flatMap { right -> divideEither(right, 2.0) }   // Right(2.0)
        .getOrElse { 0.0 }                                  // 2.0

    // comparing with nullable flow
    val result2: Double = divide2(1.0, 0.0)
        ?.let { it * 2}
        ?.let { divide2(it, 2.0)}
        ?: 0.0
}
