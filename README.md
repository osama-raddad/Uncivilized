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

## Contributing

We welcome contributions to the Uncivilized Primitives project. Please see our CONTRIBUTING.md file for details on how to contribute.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.
