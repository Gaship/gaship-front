const token = document.getElementById("_csrf").getAttribute('content');

const delivery = async (e) => {
    const content = e.target.getAttribute("data-content");
    const value = e.target.getAttribute("data-value");
    const response = await fetch(`/api/addressLocals/` + content + `?isDelivery=` + value,
        {
            method : 'PUT',
            headers: {
                'X-CSRF-TOKEN': token
            }
        });
    const result = await response.json();
    if (result === true) {
        if (e.target.classList.contains('sub-local-limit')) {
            e.target.classList.remove('sub-local-limit');
        } else {
            e.target.classList.add('sub-local-limit');
        }
    }
}

const sublist = async (e) => {
    const sub = document.getElementById('sub-address');
    while (sub.lastElementChild) {
        sub.classList.remove("active-btn");
        sub.removeChild(sub.lastElementChild)
    }


    const subAddress = e.target.value;
    const response = await fetch(`/api/addressLocals?address=${subAddress}`);
    const result = await response.json();

    result.forEach(function (element) {
        const buttonTag = document.createElement("button");
        buttonTag.setAttribute('type', 'button');
        buttonTag.setAttribute('data-content', element.addressNo);
        buttonTag.setAttribute('data-value', element.isDelivery);
        if (element.isDelivery) {
            buttonTag.setAttribute('class', 'sub-local');
        } else {
            buttonTag.setAttribute('class', 'sub-local-limit');
        }

        buttonTag.innerHTML = element.addressName
        sub.appendChild(buttonTag);

        buttonTag.addEventListener('click', delivery);
    })
}


for (let i = 0; i < 17; i++) {
    document.querySelector(`.local-${i + 1}`).addEventListener("click", sublist);
}
