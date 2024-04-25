const cards = Array.from(document.querySelectorAll(".card"));

cards.forEach((c) => {
  c.addEventListener("click", () => {
    cards.forEach((c) => c.classList.remove("active"));
    c.classList.toggle("active");
  });
});

const h1 = document.querySelector("h1");
const main = document.querySelector("main");
const h1OriginalMargin = parseInt(window.getComputedStyle(h1).marginTop);
const h1OriginalLeft = parseInt(window.getComputedStyle(h1).marginLeft);

document.addEventListener("scroll", () => {
  const scrollY = window.scrollY;
  const opacity = 1 - scrollY / 500; // Adjust 500 as needed
  h1.style.marginTop = `${h1OriginalMargin - scrollY}px`;
  h1.style.marginLeft = `${h1OriginalLeft - scrollY / 4}px`;
  h1.style.opacity = opacity < 0 ? 0 : opacity; // Ensure opacity doesn't go negative
});
