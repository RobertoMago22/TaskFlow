
const API_URL = "http://localhost:8080/api/auth";

const form = document.getElementById("formLogin");
const email = document.getElementById("email");
const senha = document.getElementById("senha");
const mensagem = document.getElementById("mensagem");
const btnLogin = document.getElementById("btnLogin");
const btnMostrarSenha = document.getElementById("mostrarSenha");


btnMostrarSenha.addEventListener("click", () => {

    const senhaVisivel = senha.type === "text";

    senha.type = senhaVisivel ? "password" : "text";

    btnMostrarSenha.textContent = senhaVisivel ? "Mostrar" : "Ocultar";
});


function limparErros() {
    document.getElementById("erroEmail").textContent = "";
    document.getElementById("erroSenha").textContent = "";

    email.classList.remove("invalid");
    senha.classList.remove("invalid");

    mensagem.hidden = true;
}

function validarFormulario() {
    limparErros();
    let valido = true;

    if (email.value.trim() === "") {
        document.getElementById("erroEmail").textContent =
            "Informe seu e-mail.";
        email.classList.add("invalid");
        valido = false;
    }

    if (senha.value === "") {
        document.getElementById("erroSenha").textContent =
            "Informe sua senha.";
        senha.classList.add("invalid");
        valido = false;
    }

    return valido;
}

form.addEventListener("submit", async evento => {
    evento.preventDefault();

    if (!validarFormulario()) return;

    btnLogin.disabled = true;
    btnLogin.textContent = "Entrando...";

    const usuario = {

        email : email.value.trim(),

        senha : senha.value

    }

    try {
        const resposta = await fetch(`${API_URL}/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(usuario)
        });

        const resultado = await resposta.json();

        if (!resposta.ok) {
            throw new Error(
                resultado.messagem ||
                "E-mail ou senha inválidos."
            );
        }

        mensagem.textContent = "Login realizado com sucesso!";
        mensagem.className = "feedback success";
        mensagem.hidden = false;

        window.location.href = "index.html";

    } catch (erro) {
        mensagem.textContent = erro.message;
        mensagem.className = "feedback error";
        mensagem.hidden = false;
    } finally {
        btnLogin.disabled = false;
        btnLogin.textContent = "Entrar";
    }
});