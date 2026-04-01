import { defineConfig } from "orval";

export default defineConfig({
  kletterapp: {
    input: {
      target: "http://localhost:8080/v3/api-docs",
    },
    output: {
      mode: "tags-split",
      target: "src/api/kletterapp.ts",
      schemas: "src/api/model",
      client: "react-query",
      clean: true,
      override: {
        fetch: {
          includeHttpResponseReturnType: false,
        },
        mutator: {
          path: "src/constants/fetcher.ts",
          name: "customFetch",
        },
      },
    },
  },
});
