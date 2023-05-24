# Uncivilized Primitives

This library aims to onboard and validate primitive data types at the edge of your system. It's designed to reduce the Primitive Obsession code smell and standardize primitive handling throughout your software system.

## Getting Started

To start using Uncivilized Primitives, you need to include it in your project's dependencies. 

## Features

The Uncivilized Primitives library provides a suite of interfaces and utilities for managing primitive data types:

- **`Civilizable<PRIMITIVE>`**: An interface for objects that can be civilized from a primitive type.
- **`Rule<PRIMITIVE>`**: An interface for creating rules to validate whether a given primitive can be civilized.
- **`Condition<PRIMITIVE, RULE : Rule<PRIMITIVE>>`**: An interface for checking whether the provided rule holds for a given primitive.
- **`Variation<PRIMITIVE, RULE : Rule<PRIMITIVE>, OBJECT : Civilizable<PRIMITIVE>>`**: An interface for applying a rule and a condition to a primitive to create a civilized object.
- **`Civilizer<PRIMITIVE, RULE : Rule<PRIMITIVE>, OBJECT : Civilizable<PRIMITIVE>>`**: A general interface for a tool that can civilize a given primitive using a set of rules and conditions.

## Example

The `RealNumberCivilizer` is a specific implementation that takes a `String` and checks if it can be parsed as a `Double`. If the string passes this rule, it is considered a valid real number.

```kotlin
val civilizer = RealNumberCivilizer()
val number: RealNumber = civilizer of (args.firstOrNull() ?: readln())
println("${number.value} is a ${number::class.simpleName}")
```

## Guide to use Uncivilized Primitives Library

**Step 1: Define Your Civilizable**

The first step is defining your Civilizable object. This object should be the result of civilizing a primitive. For example:

```kotlin
data class RealNumber(override val value: String) : Civilizable<String>
```

Here `RealNumber` is a Civilizable object that represents a real number.

**Step 2: Define Your Rules**

Next, you'll want to define your rules. Rules are used to validate whether a given primitive can be civilized. 

```kotlin
class RealNumberRule : Rule<String> {
    override fun validate(primitive: String) = primitive.toDoubleOrNull() != null
}
```

In this case, we have a rule that checks if a string can be converted to a double. If it can, it is considered a valid real number.

**Step 3: Define Your Civilizer**

This is where the magic happens. The Civilizer takes a primitive, applies the rules to it, and if the rules are passed, it civilizes the primitive.

```kotlin
class RealNumberCivilizer : Civilizer<String, Rule<String>, RealNumber>, Rule<String> {
    override fun validate(primitive: String) = primitive.toDoubleOrNull() != null
    override val variations = listOf(createVariation(RealNumber::class, allConditions(), this))
}
```

**Step 4: Use Your Civilizer**

Finally, use your Civilizer to convert primitives into civilized objects:

```kotlin
val civilizer = RealNumberCivilizer()
val number: RealNumber = civilizer of "123.456"
println("${number.value} is a ${number::class.simpleName}")
```

In this example, we're using `RealNumberCivilizer` to convert a string into a `RealNumber`. If the string can't be converted, the program will throw an error.

**Step 5: Define Additional Variations (Optional)**

In some cases, you might want to handle a primitive in different ways depending on its characteristics. For example, you might have different rules for strings that represent integers and strings that represent floating-point numbers. In these cases, you can define additional Variations:

```kotlin
class IntegerRule : Rule<String> {
    override fun validate(primitive: String) = primitive.toIntOrNull() != null
}

val civilizer = RealNumberCivilizer()
civilizer.variations += civilizer.createVariation(RealNumber::class, allConditions(), IntegerRule())
```

In this example, we're adding a new variation that handles strings which can be converted to integers. Now, our `RealNumberCivilizer` can handle both floating-point and integer numbers.

## Contributing

We welcome contributions to the Uncivilized Primitives project. Please see our CONTRIBUTING.md file for details on how to contribute.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
