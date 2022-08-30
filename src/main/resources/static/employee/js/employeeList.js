const token = document.getElementById('_csrf').getAttribute('content');

const deleteEmployee = async (e) =>{

    const employeeNo = e.target.getAttribute("data-employee-no");
    const response = await fetch(`/api/employees/`+ employeeNo,
        {
            method : 'DELETE',
            headers: {
                'X-CSRF-TOKEN': token
            }
        }).then(()=> window.location.reload());
}

document.querySelectorAll('.deleteButton')
    .forEach(element => element.addEventListener('click',deleteEmployee))