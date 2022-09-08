const parseKoreaCurrency = (amount) => new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' })
  .format(amount);

document.querySelectorAll(".product-amount")
  .forEach(elem => {
    const amount = elem.textContent;
    elem.textContent = parseKoreaCurrency(+amount);
  })
