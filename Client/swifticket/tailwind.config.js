/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './src/*/*.{html,js,jsx}',
  ],
  theme: {
    extend: {
      spacing: {
        'df': '4rem',
        'df-sm': '2rem',
        'df-xs': '1rem',
        'df-lg': '6rem',
        'df-xl': '8rem',
        'df-2xl': '16rem',
      },
      padding: {
        'df': '1rem',
        'df-sm': '0.5rem',
        'df-xs': '0.25rem',
        'df-lg': '2rem',
        'df-xl': '4rem',
        'df-2xl': '8rem',
      }
    },
  },
  plugins: [],
}