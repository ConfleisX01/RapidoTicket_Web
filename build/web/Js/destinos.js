export function cargarControles() {
    let btnAgregarDestino = document.getElementById('btnAgregarDestino')

    btnAgregarDestino.addEventListener('click', () => {
        if (verificarInputs()) {
            agregarDestino()
        }
    })
}

async function agregarDestino() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/destino/agregarDestino'
    let nombreDestino = document.getElementById('txtNombreDestino').value

    const formData = new URLSearchParams()
    formData.append('destino', nombreDestino)

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    }

    try {
        const response = await fetch(URL, requestOptions)
        if (response.ok) {
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Destino agregado con exito",
                showConfirmButton: false,
                timer: 1500
            });
            fixSwal()
            cargarTabla()
        } else {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Error al agregar el destino",
            });
            fixSwal()
        }
    } catch (error) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: `${error.message}`,
        });
        fixSwal()
    }
}

export async function cargarTabla() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/destino/getAllDestinos'
    let data = await getAllData(URL)
    let tblDestinos = document.getElementById('tblDestinos')
    tblDestinos.innerHTML = ""

    data.forEach(element => {
        let content = `
                    <tr>
                        <th scope="col">${element.idDestino}</th>
                        <th scope="col">${element.destinos}</th>
                    </tr>`
        tblDestinos.innerHTML += content
    });
}

function fixSwal() {
    document.body.classList.remove('swal2-height-auto')
}

function verificarInputs() {
    let nombreDestino = document.getElementById('txtNombreDestino').value

    if (nombreDestino == "") {
        return false
    }

    return true
}

async function getAllData(URL) {
    try {
        const response = await fetch(URL)
        const json = await response.json()
        return json;
    } catch (error) {
        console.log(error)
    }
}