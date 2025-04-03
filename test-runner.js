#!/usr/bin/env node

const { execSync } = require("child_process");

const args = require("minimist")(process.argv.slice(2));
const type = args.type || "all"; //se puede mandar como parametros ui, api, all
const tag = args.tag; // optional para filtrar por algunos tests


//logica para correr tests de api
const runApiTests = () => {
  let command = "mvn test -f ./API_tests/pom.xml";
  if (tag) {
    command += ` -Dgroups=${tag}`;
  }
  execSync(command, { stdio: "inherit" });
};

//logica para correr tests ui
const runUiTests = () => {
  let command = "npx playwright test";
  if (tag) {
    command += ` --grep @${tag}`;
  }
  execSync(command, { cwd: "./UI_tests", stdio: "inherit" });
};

// switch para determinar que correr
if (type === "all") {
  runApiTests();
  runUiTests();
} else if (type === "api") {
  runApiTests();
} else if (type === "ui") {
  runUiTests();
} else {
  console.error("Usar --type=api | ui | all");
  process.exit(1);
}