import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
  plugins: [vue()],
  optimizeDeps: {
    include: ['fast-deep-equal']
  },
  server: {
    proxy: {
      '/earthquakes': 'http://localhost:8080'
    }
  }
});
