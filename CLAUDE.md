# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**mingsha-kernel** is a Java foundation toolkit (JDK 8+) for enterprise application development. It provides reusable utilities across multiple modules: constants, core utilities, logging, common models, and testing.

**License**: MIT | **Build**: Maven 3.6+

## Build Commands

```bash
make help           # Show all available commands
make clean          # Clean build artifacts
make test           # Run all unit tests
make package        # Package all modules (set SKIP_TEST=true to skip tests)
make install        # Install to local Maven repository
make set-version NEW_VERSION=xxx  # Bump version via scripts
```

Or use Maven directly: `./mvnw <goal>`

## Architecture

**Maven multi-module project** with 6 modules under group `site.mingsha.kernel`:

| Module | Purpose |
|--------|---------|
| `mingsha-kernel-constants` | Global constants (no external dependencies) |
| `mingsha-kernel-core` | Core utilities: thread pool, HTTP, ID generation, rate limiting, filters, validation, etc. |
| `mingsha-kernel-logger` | Structured logging with MDC tracing |
| `mingsha-kernel-model` | Request/Response DTOs |
| `mingsha-kernel-test` | Shared test utilities |
| `mingsha-kernel-all` | Aggregator module bundling all modules |

**Package base**: `site.mingsha.kernel`

**Core module key packages**:
- `thread/` — `MingshaThreadPool`, `MdcThreadPoolExecutor`
- `id/` — `UUIDGenerator`, `SnowflakeIdGenerator`
- `limit/` — `MingshaRateLimiter` (token bucket)
- `http/` — `OkHttpUtils`
- `filter/` — `MingshaBloomFilter`
- `utils/` — date, regex, validation, bean mapping, compression, MD5, timing utilities

**Logger module**: `LogUtils` (MDC-based), `MingshaLog` (structured log model), `LogPattern`

**Model module**: `RequestDTO` (with traceId), `ResponseDTO` (generic)

## Code Patterns

**Utility classes**: Private no-arg constructor to prevent instantiation; heavy use of static methods.
```java
public class LogUtils {
    private LogUtils() {}
    public static void info(String message) { ... }
}
```

**Builder pattern** for objects with many optional fields:
```java
MingshaLog log = MingshaLog.builder().traceId("xxx").module("order").message("ok").build();
```

**Factory static methods** for object creation:
```java
public static ResponseDTO success() { return new ResponseDTO(); }
```

## Version Management

Uses `flatten-maven-plugin` with `${revision}` property for CI-friendly versioning. Scripts in `/bin/set-version.sh`.

## Testing

**Framework**: JUnit Jupiter 5.11.4. Tests live in `mingsha-kernel-test/src/test/java/`.

Run a single test:
```bash
./mvnw test -D test=ListSplitUtilsTest -DfailIfNoTests=false
```
