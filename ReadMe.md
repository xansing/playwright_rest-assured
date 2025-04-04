QA automation framework: playwright + restassured.

Technologias usadas:
- Playwright
- Rest assured
- Junit 5
- sistemas de tageo para tests
- Test data externo (json)
- Script de ejecucion general

Requisitos:
1) token de gorest debera ser agregado como variable de entorno
    nombre de variable `GOREST_TOKEN`


Correr los Tests:
    -UI Tests (Playwright):
        a) cd UI_tests
        b) npx playwright test

        opcional por tag:
        c) npx playwright test --grep @smoke

    -API Tests (Rest Assured):
        a)cd API_tests
        b) mvn test

        opcional por tag:
        c) mvn test -Dgroups=api

    - ejecucion general
        ui) node test-runner.js --ui
        api) node test-runner.js --api
        ui + api) node test-runner.js --ui --api
        ui + filtro) node test-runner.js --ui --tag=@smoke
        api + filtro) node test-runner.js --api --tag=smoke

    
To Do List:
1) Integrar reportes
2) Validaciones visuales para preubas ui
3) Dockerizar proyecto
4) integrar Ci/CD
