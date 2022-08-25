document.querySelector(".email-verify").addEventListener('click', async () => {
  const email = document.querySelector(".sign-up-email").value;
  const res = await fetch(`/api/members/verify?email=${email}`);
  const messageJson = await res.json();

  if(messageJson.hasOwnProperty("message")){
    const toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
    });

    toast.fire({
      icon: 'success',
      title: messageJson.message
    });
  }
})
