export function enviarReporte() {
    let empleado = localStorage.getItem('numeroEmpleado')
    let txtEmpId = document.getElementById('txtEmpId')
    let txtEmpProblem = document.getElementById('txtEmpProblem').value

    if (empleado) {

        txtEmpId.value = empleado

        if (validarInputs()) {
            // TODO: Agregar la logica para enviar el mensaje
        } else {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Ingresa el mensaje para poder enviar el reporte",
            });
            fixSwal()
        }
    } else {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Tienes que iniciar sesion para poder enviar un reporte",
        });
        fixSwal()
    }
}

function validarInputs() {
    let txtEmpProblem = document.getElementById('txtEmpProblem').value

    if (txtEmpProblem == "") {
        return false
    }

    return true
}

function fixSwal() {
    document.body.classList.remove('swal2-height-auto')
}