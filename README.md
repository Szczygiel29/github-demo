# GitHub Repositories Service

## Opis

Projekt umożliwia wyświetlenie listy repozytoriów użytkownika GitHub. Aplikacja wykorzystuje API GitHub do pobierania danych.

## Technologia

- Java 21
- Spring Boot 3
- Gradle
- GitHub Actions (CI/CD)

## Jak uruchomić projekt

### Prerekwizyty

- Zainstalowany JDK 21
- Zainstalowany Gradle

### Uruchomienie aplikacji

1. Sklonuj repozytorium:
    ```bash
    git clone https://github.com/Szczygiel29/github-demo
    ```

2. Uruchom aplikację:
    ```bash
    ./gradlew bootRun
    ```

Aplikacja będzie dostępna pod adresem: `http://localhost:8080`.

### Przykładowe wywołanie endpointu

Aby sprawdzić działanie endpointu, wywołaj poniższy URL w przeglądarce lub za pomocą narzędzia takiego jak `curl` lub `Postman`:

http://localhost:8080/api/github/user/Szczygiel29/repositories

### Testowanie

Uruchamianie testów jednostkowych
Aby uruchomić testy jednostkowe, użyj polecenia:
```bash
./gradlew test
```

### CI/CD
Projekt jest zintegrowany z GitHub Actions do automatycznego uruchamiania testów jednostkowych po każdym commicie. Plik konfiguracyjny znajduje się w .github/workflows/build.yml.
W sekcji Action można srpawdzić proces CI/CD.
