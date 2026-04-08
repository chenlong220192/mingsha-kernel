# Mingsha Kernel

> Efficient and general-purpose Java foundation toolkit, covering constants, core utilities, logging, common models, testing modules, etc., to assist enterprise-level application development.

---

## Module Structure

| Module                  | Description | Documentation |
|------------------------|------------|--------------|
| mingsha-kernel-constants | Global constants definition | [View](./mingsha-kernel-constants/README.md) |
| mingsha-kernel-core    | Core utilities: thread pool, ID generation, rate limiting, HTTP, Bean mapping, date, regex, compression, exception, collection grouping, validation, IP, timing, etc. | [View](./mingsha-kernel-core/README.md) |
| mingsha-kernel-logger  | Logging utilities with structured logging, MDC tracing, log format templates | [View](./mingsha-kernel-logger/README.md) |
| mingsha-kernel-model   | Common request/response data structures for microservices | [View](./mingsha-kernel-model/README.md) |
| mingsha-kernel-test    | Unit and integration test cases | [View](./mingsha-kernel-test/README.md) |
| mingsha-kernel-all     | Aggregates all modules for unified dependency management | - |

---

## 🚀 快速开始

## Environment Requirements
- JDK 17 or higher
- Maven 3.6+

## Quick Start
```sh
make package SKIP_TEST=true
```

## Build Commands

| Command            | Description                     |
|-------------------|--------------------------------|
| make help         | Display all available commands  |
| make clean        | Clean build artifacts          |
| make test         | Run all unit tests             |
| make package      | Package all modules            |
| make install      | Install to local repository    |
| make deploy       | Deploy to remote repository   |
| make set-version  | Set project version number    |

> Note: Use `SKIP_TEST=true` to skip tests, and `NEW_VERSION=xxxx` to specify version number.

## Version Management
```sh
make set-version NEW_VERSION=2025.07.10.01
```

---

## Contributing Guidelines

1. Fork the repository and create a new branch for development.
2. Maintain consistent code style and complete documentation.
3. Ensure all unit tests pass before committing.
4. Provide detailed description when submitting PRs.

---

## Important Notes
- `-SNAPSHOT` indicates a snapshot version for development. Remove it before release.
- For deploying to private repository, configure username and password in `settings.xml`, ensuring `server.id` matches `pom.xml`.
- Force update snapshots: `mvn clean package -U`

---

## Contact
For questions or suggestions, feel free to open an Issue or submit a PR.

## License
This project is licensed under MIT License. See [LICENSE](./LICENSE) for details.
