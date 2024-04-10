export function cargarControles() {
    let btnAgregarEmpelado = document.getElementById('btnAgregarEmpleado')

    btnAgregarEmpelado.addEventListener('click', () => {
        if (verificarInputs()) {
            agregarEmpleado()
        }
    })
}

async function agregarEmpleado() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/agregarEmpleado'
    let nombre = document.getElementById('txtNombreEmpleado').value
    let apellidos = document.getElementById('txtApellidosEmpleado').value
    let numeroEmpleado = document.getElementById('txtNumeroEmpleado').value
    let telefono = document.getElementById('txtTelefonoEmpleado').value
    let foto = document.getElementById('txtFoto').files[0]

    function convertirImagenABase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => resolve(reader.result)
            reader.onerror = reject
            reader.readAsDataURL(file)
        })
    }

    let fotoBase64 = await convertirImagenABase64(foto)

    let empleado = {
        "persona": {
            "nombre": nombre,
            "apellidos": apellidos,
            "telefono": telefono
        },
        "numeroEmpleado": numeroEmpleado,
        "foto": fotoBase64
    };

    const formData = new URLSearchParams()
    formData.append('empleado', JSON.stringify(empleado))

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
            limpiarCampos()
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Empleado agregado con éxito",
                showConfirmButton: false,
                timer: 1500
            });
            fixSwal()
            cargarTabla()
        } else {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Error al agregar el empleado",
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

function limpiarCampos() {
    document.getElementById('txtNombreEmpleado').value = '';
    document.getElementById('txtApellidosEmpleado').value = '';
    document.getElementById('txtNumeroEmpleado').value = '';
    document.getElementById('txtTelefonoEmpleado').value = '';
}


function verificarInputs() {
    let nombre = document.getElementById('txtNombreEmpleado').value
    let apellidos = document.getElementById('txtApellidosEmpleado').value
    let numeroEmpleado = document.getElementById('txtNumeroEmpleado').value
    let telefono = document.getElementById('txtTelefonoEmpleado').value
    let foto = document.getElementById('txtFoto')

    if (nombre == "") {
        return false
    }

    if (apellidos == "") {
        return false
    }

    if (numeroEmpleado == "") {
        return false
    }

    if (telefono == "") {
        return false
    }

    if (foto.files.lenght < 0) {
        return false
    }

    return true
}

function fixSwal() {
    document.body.classList.remove('swal2-height-auto')
}

export async function cargarTabla() {
    const URL = 'http://localhost:8080/DreamSoft_RapidoTicket/api/empleado/getAllEmpleados'
    let data = await getAllData(URL);
    let tblEmpleados = document.getElementById('tblEmpleados')

    tblEmpleados.innerHTML = ""

    data.forEach(element => {
        let content = `
            <tr>
                <th scope="col">${element.idEmpleado}</th>
                <th scope="col">
                    <div class="avatar-img">
                        <img src="${element.foto}" alt="Avatar">
                    </div>
                </th>
                <th scope="col">${element.numeroEmpleado}</th>
                <th scope="col">${element.persona.nombre}</th>
                <th scope="col">${element.persona.apellidos}</th>
                <th scope="col"><span class="${element.usuario == null ? "badge text-bg-secondary" : "badge text-bg-primary"}">${element.usuario == null ? "Registro Pendiente" : "Registrado"}</span></th>
            </tr>`;
        tblEmpleados.innerHTML += content
    });
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