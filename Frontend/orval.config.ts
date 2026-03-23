import { defineConfig } from "orval";

export default defineConfig({
  kletterapp: {
    output: {
      mode: "tags-split",
      target: "src/api/kletterapp.ts",
      schemas: "src/api/model",
      client: "react-query",
      mock: true,
    },
    input: {
      target: "http://localhost:8080/v3/api-docs",
    },
  },
});
