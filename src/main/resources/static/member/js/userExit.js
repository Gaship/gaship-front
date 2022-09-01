document.querySelector('.exit-btn')
  .addEventListener('click', () => {
    const result = confirm("정말로 탈퇴하시겠습니까?");
    const token = document.getElementById("_csrf").content
    const tokenHeader = document.getElementById("_csrf_header").content;
    if(result) {
      fetch(`/api/members`, {
        method : 'DELETE',
        headers : {
          [`${tokenHeader}`] : token,
        }
      })
        .then(() => {
          location.replace("/logout");
        });
    }
  })
