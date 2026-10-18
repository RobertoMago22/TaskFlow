
const API_URL = "https://taskflow-rpnb.onrender.com/api/auth";

const form = document.getElementById("formCadastro");

const nome = document.getElementById("nome");
const email = document.getElementById("email");
const senha = document.getElementById("senha");
const confirmarSenha = document.getElementById("confirmarSenha");
const mensagem = document.getElementById("mensagem");
const btnCadastrar = document.getElementById("btnCadastrar");
const btnMostrarSenha = document.getElementById("mostrarSenha");
const btnConfirmarSenha = document.getElementById("mostrarConfirmarSenha");

btnMostrarSenha.addEventListener("click", () => {

    const senhaVisivel = senha.type === "text";

    senha.type = senhaVisivel ? "password" : "text";

    btnMostrarSenha.textContent = senhaVisivel ? "Mostrar" : "Ocultar";
});


btnConfirmarSenha.addEventListener("click", () => {

    const senhaVisivel = confirmarSenha.type === "text";

    confirmarSenha.type = senhaVisivel ? "password" : "text";

    btnConfirmarSenha.textContent = senhaVisivel ? "Mostrar" : "Ocultar";
});


function limparErros() {
    ["Nome", "Email", "Senha", "ConfirmarSenha"].forEach(campo => {
        document.getElementById(`erro${campo}`).textContent = "";
    });

    [nome, email, senha, confirmarSenha].forEach(input => {
        input.classList.remove("invalid");
    });

    mensagem.hidden = true;
}

function mostrarErro(input, id, texto) {
    document.getElementById(id).textContent = texto;
    input.classList.add("invalid");
}

function validarFormulario() {
    limparErros();
    let valido = true;

    if (nome.value.trim() === "") {
        mostrarErro(nome, "erroNome", "Informe seu nome.");
        valido = false;
    }

    const emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailValido.test(email.value.trim())) {
        mostrarErro(email, "erroEmail", "Informe um e-mail válido.");
        valido = false;
    }

    if (senha.value.length < 8) {
        mostrarErro(senha, "erroSenha",
            "A senha deve ter pelo menos 8 caracteres.");
        valido = false;
    }

    if (confirmarSenha.value !== senha.value ||
        confirmarSenha.value === "") {
        mostrarErro(confirmarSenha, "erroConfirmarSenha",
            "As senhas não coincidem.");
        valido = false;
    }

    return valido;
}

form.addEventListener("submit", async evento => {
    
    evento.preventDefault();

    mensagem.hidden = true;
    mensagem.classList.remove("success", "error");

    if (!validarFormulario()) return;

    const usuario = {
        nome: nome.value.trim(),
        email: email.value.trim(),
        senha: senha.value,
    };

    btnCadastrar.disabled = true;
    btnCadastrar.textContent = "Cadastrando...";

    try {
        const resposta = await fetch(`${API_URL}/cadastro`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(usuario),
            credentials: "include"
        });

        const resultado = await resposta.json();

        if (!resposta.ok) {
            throw new Error(
                resultado.mensagem ||
                "Não foi possível realizar o cadastro."
            );
        }

        mensagem.textContent = "Cadastro realizado com sucesso!";
        mensagem.classList.add("success");
        mensagem.hidden = false;

        form.reset();

        setTimeout(() => {
            window.location.href = "login.html";
        }, 1200);

    } catch (erro) {
        mensagem.textContent = erro.message;
        mensagem.classList.add("error");
        mensagem.hidden = false;
    } finally {
        btnCadastrar.disabled = false;
        btnCadastrar.textContent = "Criar conta";
    }
});