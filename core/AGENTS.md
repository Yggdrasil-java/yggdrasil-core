# Repository Guidelines

## Module Structure

This directory builds the `dev.yggdrasil:yggdrasil-core` artifact. Production
sources belong to `src/main/java/dev/yggdrasil/core/domain` or `application`.
The JPMS module remains `dev.yggdrasil.core`.

The domain owns immutable rules, inputs, results, invariants, and comparison
policies. `domain.internal` is not exported. Application services depend on
the domain. Keep HTTP, persistence, and business actions in external consumers.

## Development Commands

Run commands from the repository root with JDK 25:

- `./mvnw -pl core test`: run the JUnit Jupiter contracts.
- `./mvnw verify`: package the library.
- `python3 scripts/verify_architecture.py`: inspect compiled layer dependencies.
- `python3 scripts/verify_consumer.py`: execute the packaged API scenarios.
- `mise run build`: build and check architecture using configured tools.

Build output belongs in `core/target`; release candidates belong in the root
`target/releases` directory. Maven artifact names differ from directory names.

## Tests and API Changes

Mirror production layers under `src/test/java`. Keep shared rule builders in
`support/RuleFixtures.java`. Test observable contracts, including UNKNOWN,
short-circuit explanations, immutable snapshots, and explicit value capture.
Run architecture verification after changing production dependencies.

Update `docs/` specifications before changing contracts. Public imports use
`dev.yggdrasil.core.domain` and `dev.yggdrasil.core.application`; update both
independent consumers when signatures change. Regenerate the release candidate
and review its exported signatures before requesting stable API approval.

Use four spaces, PascalCase types, camelCase methods, and UPPER_SNAKE_CASE
constants. Preserve licensing resources and the Java-only production dependency
contract. Never introduce dependencies from domain to application.
