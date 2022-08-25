document.querySelectorAll(".deleteButton")
  .forEach(element => {
    element.addEventListener('click', (event) => {
      const couponNo = event.target.getAttribute("data-coupon-no");
      const csrfToken = document.getElementById("_csrf").getAttribute("content");
      fetch(`/admin/coupons/coupon-type/${couponNo}`, {
        method : 'DELETE',
        headers: {'X-CSRF-TOKEN': csrfToken}
      })
      .then(() => document.location.reload());
    });
  })
