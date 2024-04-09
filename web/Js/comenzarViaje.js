export async function comenzarViaje() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/activar'
    let idCamion = document.getElementById('txtIdCamion').value

    if (verificarInputs()) {
        const formData = new URLSearchParams()
        formData.append('idCamion', idCamion)

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
                    title: "Comenzando Viaje",
                    showConfirmButton: false,
                    timer: 1500
                });
                guardarViaje(idCamion)
                VerificarViaje()
                fixSwal()
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error al comenzar el viaje",
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
}

function verificarInputs() {
    let idCamion = document.getElementById('txtIdCamion').value

    if (idCamion == "") {
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

export async function VerificarViaje() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/getAllCamiones'

    const idCamion = localStorage.getItem('idCamion')

    if (idCamion) {
        let data = await getAllData(URL)

        let qr = data.filter(item => item.idCamion == idCamion)

        colocarCodigo(qr[0].qr)
    }
}

function guardarViaje(idCamion) {
    localStorage.setItem('idCamion', idCamion);
}

async function eliminarViaje() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/camion/desactivar'
    const idCamion = localStorage.getItem('idCamion')

    const formData = new URLSearchParams()
        formData.append('idCamion', idCamion)

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
                    title: "Comenzando Viaje",
                    showConfirmButton: false,
                    timer: 1500
                });
                guardarViaje(idCamion)
                VerificarViaje()
                fixSwal()
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error al comenzar el viaje",
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

    localStorage.removeItem('idCamion');
    regresarModulo()
}

function regresarModulo() {
    window.location.href = "index.html";
}

async function colocarCodigo(qr) {
    let contenedor = document.getElementById('code-container');
    contenedor.innerHTML = '';
    let contenido = `
    <div class="p-0 m-0 h-100 d-flex align-items-center justify-content-center flex-column" id="code-container">
        <div class="code-card p-2">
            <h5 class="text-primary">Código de Viaje</h5>
            <h3>Escanea este código con la aplicación para registrarte en el camión</h3>
            <div class="py-4">
                <img src="data:image/jpeg;base64,${qr}" alt="Código QR" width="256px" height="256px">
            </div>
            <div class="">
                <button class="btn-secondary" id="btnTerminarViaje">
                    Terminar Viaje
                </button>
            </div>
        </div>
    </div>`;

    contenedor.innerHTML = contenido;

    let btnTerminarViaje = document.getElementById('btnTerminarViaje')

    btnTerminarViaje.addEventListener('click', () => {
        eliminarViaje()
    })
}


function fixSwal() {
    document.body.classList.remove('swal2-height-auto')
}