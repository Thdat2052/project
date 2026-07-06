import react from "@vitejs/plugin-react";
import path from "path";
import { defineConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig(({ command }) => ({
  base: command === "serve" ? "/" : "/personal_project_front-end/",
  plugins: [react()],
  define: {
    "process.env": {},
    global: "window",
  },
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src/"),
    },
  },
}));
