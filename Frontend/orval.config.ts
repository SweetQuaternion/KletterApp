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
        query: {
          options: {
            retry: false,
            refetchOnReconnect: false,
          },
        },
        fetch: {
          includeHttpResponseReturnType: false,
        },
        mutator: {
          path: "src/utils/fetcher.ts",
          name: "customFetch",
        },
      },
    },
  },
});
