document.querySelector(".email-verify").addEventListener('click', async () => {
  const email = document.querySelector(".sign-up-email").value;

  if(email === '') {
    return Swal.fire(
      '이메일이 없습니다.',
      '인증할 이메일이 없습니다.',
      'error'
    );
  }

  const res = await fetch(`/api/members/verify?email=${email}`);
  const messageJson = await res.json();


  if(!res.ok){
    return Swal.fire(
      res.status + ' : 에러 발생',
      messageJson.errorMessage,
      'error'
    );
  }


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

document.querySelector('.recommend-btn').addEventListener('click', async () => {
  const recommendNickname = document.querySelector('.recommend-nickname');
  const token = document.getElementById('_csrf');
  const tokenHeader = document.getElementById('_csrf_header');

  if(recommendNickname.value === '') {
    return Swal.fire(
      '추천인 닉네임을 입력하세요.',
      '추천인 닉네임 작성 후 추천인 조회가 가능합니다.',
      'error'
    );
  }


  const response = await fetch('/api/members/recommend', {
    method : 'post',
    headers : {
      "Content-Type": "application/json",
      [`${tokenHeader.content}`] : token.content
    },
    body : JSON.stringify({
      nickname : recommendNickname.value
    })
  })

  if(!response.status === 500) {
    return Swal.fire(
      '추천 멤버가 존재하지 않음.',
      '추천하시려는 멤버가 존재하지 않습니다.',
      'warning'
    );
  }

  if(!response.ok){
    return Swal.fire(
      response.status + ' : 에러 발생',
      (await response.json()).errorMessage,
      'error'
    );
  }

  Swal.fire(
    '추천인 확인완료.',
    '추천인 확인이 완료되었습니다.',
    'success'
  );
});

document.querySelector('.check-duplication').addEventListener('click', async () => {
  const checkTargetNickname = document.querySelector('.target-nickname');
  const token = document.getElementById('_csrf');
  const tokenHeader = document.getElementById('_csrf_header');

  if(checkTargetNickname.value === '') {
    return Swal.fire(
      '닉네임을 입력해주세요.',
      '닉네임이 필요합니다.',
      'error'
    );
  }

  const response = await fetch('/api/members/check-nickname', {
    method : 'post',
    headers : {
      [`${tokenHeader.content}`] : token.content,
      "Content-Type": "application/json"
    },
    body : JSON.stringify({
      nickname : checkTargetNickname.value
    })
  });

    if(!response.status === 500) {
    return Swal.fire(
      '존재하는 닉네임',
      '이미 존재하는 닉네임입니다.',
      'warning'
    );
  }

  if(!response.ok){
    return Swal.fire(
      response.status + ' : 에러 발생',
      (await response.json()).errorMessage,
      'error'
    );
  }

  Swal.fire(
    '중복확인 완료.',
    '중복되는 닉네임이 없습니다. 회원가입을 진행해주세요.',
    'success'
  );
});
