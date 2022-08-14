const path = document.location.pathname;

if(path.match(/\/admin\/members+/g)) {
    document.querySelector(".customer-info").classList.add("menu-active");
}
