
const API_URL = "https://taskflow-rpnb.onrender.com/api/tarefas";

// Elementos do HTML
const corpoTabela = document.getElementById("corpoTabela");
const modal = document.getElementById("modal");
const formTarefa = document.getElementById("formTarefa");
const mensagem = document.getElementById("mensagem");
const busca = document.getElementById("busca");
const filtroStatus = document.getElementById("filtroStatus");
const filtroPrioridade = document.getElementById("filtroPrioridade");
const avatar = document.getElementById("avatar");
const nomeUsuario = document.getElementById("nomeUsuario");
const btnSair = document.getElementById("deslogar");

const campos = {
    id: document.getElementById("idTarefa"),
    titulo: document.getElementById("titulo"),
    descricao: document.getElementById("descricao"),
    prioridade: document.getElementById("prioridade"),
    status: document.getElementById("status"),
    prazo: document.getElementById("prazo")
};

let tarefas = [];

// Requisição centralizada
async function requisicao(url, opcoes = {}) {
    const resposta = await fetch(url, {
        ...opcoes,
        headers: {
            "Content-Type": "application/json",
            ...opcoes.headers
        }
    });

    if (!resposta.ok) {
        let detalhe = `Erro HTTP ${resposta.status}`;

        try {
            const dados = await resposta.json();
            detalhe = dados.mensagem || dados.message || detalhe;
        } catch {
            // A resposta pode não conter JSON.
        }

        throw new Error(detalhe);
    }

    if (resposta.status === 204) {
        return null;
    }

    return resposta.json();
}

// Exibe mensagens para o usuário
function exibirMensagem(texto, tipo = "success") {
    mensagem.textContent = texto;
    mensagem.className = `message ${tipo}`;
    mensagem.hidden = false;
}

function limparMensagem() {
    mensagem.hidden = true;
    mensagem.textContent = "";
}

// Carregar nome do Usuario
async function carregarNome() {

    const resposta = await requisicao(`${API_URL}/usuario`, {

        method: "GET",
        credentials: "include"
    });

    const nome = resposta.nome;
    const sigla = nome[0].toUpperCase();

    const nomeFinal = nome.split(" ")
        .slice(0, 2)
        .map(palavra => palavra[0].toUpperCase() + palavra.slice(1))
        .join(" ");

    avatar.textContent = sigla;
    nomeUsuario.textContent = nomeFinal;
}

// Carregar tarefas
async function carregarTarefas() {
    corpoTabela.innerHTML = `
        <tr>
            <td colspan="5" class="empty">Carregando...</td>
        </tr>
    `;

    try {
        const dados = await requisicao(API_URL, {
            method: "GET",
            credentials: "include"
        });

        if (!Array.isArray(dados.tarefas)) {
            throw new Error("A API deve retornar uma lista de tarefas.");
        }

        tarefas = dados.tarefas;
        atualizarDashboard();
        aplicarFiltros();
    } catch (erro) {
        tarefas = [];
        atualizarDashboard();

        corpoTabela.innerHTML = `
            <tr>
                <td colspan="5" class="empty">
                    Não foi possível carregar as tarefas.
                </td>
            </tr>
        `;

        exibirMensagem(erro.message, "error");
    }
}

// Dashboard
function atualizarDashboard() {
    document.getElementById("totalTarefas").textContent = tarefas.length;

    document.getElementById("totalPendentes").textContent =
        tarefas.filter(t => t.status === "PENDENTE").length;

    document.getElementById("totalAndamento").textContent =
        tarefas.filter(t => t.status === "EM_ANDAMENTO").length;

    document.getElementById("totalConcluidas").textContent =
        tarefas.filter(t => t.status === "CONCLUIDA").length;
}

// Filtros locais
function aplicarFiltros() {
    const termo = busca.value.trim().toLowerCase();
    const statusSelecionado = filtroStatus.value;
    const prioridadeSelecionada = filtroPrioridade.value;

    const resultado = tarefas.filter(tarefa => {
        const correspondeTitulo =
            (tarefa.titulo || "").toLowerCase().includes(termo);

        const correspondeStatus =
            !statusSelecionado || tarefa.status === statusSelecionado;

        const correspondePrioridade =
            !prioridadeSelecionada ||
            tarefa.prioridade === prioridadeSelecionada;

        return correspondeTitulo &&
            correspondeStatus &&
            correspondePrioridade;
    });

    renderizarTarefas(resultado);
}

// Traduz valores para exibição
function textoStatus(status) {
    const opcoes = {
        PENDENTE: "Pendente",
        EM_ANDAMENTO: "Em andamento",
        CONCLUIDA: "Concluída"
    };

    return opcoes[status] || status;
}

function textoPrioridade(prioridade) {
    const opcoes = {
        BAIXA: "Baixa",
        MEDIA: "Média",
        ALTA: "Alta"
    };

    return opcoes[prioridade] || prioridade;
}

function formatarData(data) {
    if (!data) return "Sem prazo";

    const partes = data.split("-");

    if (partes.length !== 3) return data;

    return `${partes[2]}/${partes[1]}/${partes[0]}`;
}

// Renderização
function renderizarTarefas(lista) {
    corpoTabela.replaceChildren();

    if (lista.length === 0) {
        const linha = document.createElement("tr");
        const coluna = document.createElement("td");

        coluna.colSpan = 5;
        coluna.className = "empty";
        coluna.textContent = "Nenhuma tarefa encontrada.";

        linha.appendChild(coluna);
        corpoTabela.appendChild(linha);
        return;
    }

    lista.forEach(tarefa => {
        const linha = document.createElement("tr");

        const colunaTarefa = document.createElement("td");
        const titulo = document.createElement("span");
        const descricao = document.createElement("small");

        titulo.className = "task-title";
        titulo.textContent = tarefa.titulo;

        descricao.className = "task-description";
        descricao.textContent = tarefa.descricao || "Sem descrição";

        colunaTarefa.append(titulo, descricao);

        const colunaPrioridade = document.createElement("td");
        const prioridade = document.createElement("span");

        prioridade.className = `pill priority-${tarefa.prioridade}`;
        prioridade.textContent = textoPrioridade(tarefa.prioridade);
        colunaPrioridade.appendChild(prioridade);

        const colunaStatus = document.createElement("td");
        const status = document.createElement("span");

        status.className = `pill status-${tarefa.status}`;
        status.textContent = textoStatus(tarefa.status);
        colunaStatus.appendChild(status);

        const colunaPrazo = document.createElement("td");
        colunaPrazo.textContent = formatarData(tarefa.prazo);

        const colunaAcoes = document.createElement("td");
        const acoes = document.createElement("div");
        acoes.className = "actions";

        const editar = document.createElement("button");
        editar.type = "button";
        editar.className = "action-button";
        editar.textContent = "Editar";
        editar.addEventListener("click", () => abrirEdicao(tarefa.id));

        const excluir = document.createElement("button");
        excluir.type = "button";
        excluir.className = "action-button delete";
        excluir.textContent = "Excluir";
        excluir.addEventListener("click", () => excluirTarefa(tarefa.id));

        const alterarStatus = document.createElement("select");
        alterarStatus.className = "action-button";
        alterarStatus.setAttribute("aria-label", `Alterar status de ${tarefa.titulo}`);

        const opcoesStatus = [
            ["PENDENTE", "Pendente"],
            ["EM_ANDAMENTO", "Em andamento"],
            ["CONCLUIDA", "Concluída"]
        ];

        opcoesStatus.forEach(([valor, texto]) => {
            const opcao = document.createElement("option");
            opcao.value = valor;
            opcao.textContent = texto;
            alterarStatus.appendChild(opcao);
        });

        alterarStatus.value = tarefa.status;

        alterarStatus.addEventListener("change", async () => {
            alterarStatus.disabled = true;

            try {
                await requisicao(`${API_URL}/${tarefa.id}/status`, {
                    method: "PATCH",
                    credentials: "include",
                    body: JSON.stringify(alterarStatus.value)
                });

                exibirMensagem("Status atualizado com sucesso.");
                await carregarTarefas();
            } catch (erro) {
                exibirMensagem(erro.message, "error");
                alterarStatus.value = tarefa.status;
            } finally {
                alterarStatus.disabled = false;
            }
        });

        acoes.append(editar, alterarStatus, excluir);
        colunaAcoes.appendChild(acoes);

        linha.append(
            colunaTarefa,
            colunaPrioridade,
            colunaStatus,
            colunaPrazo,
            colunaAcoes
        );

        corpoTabela.appendChild(linha);
    });
}

// Modal
function abrirModal() {
    modal.hidden = false;
    campos.titulo.focus();
}

function fecharModal() {
    modal.hidden = true;
    formTarefa.reset();
    campos.id.value = "";
    limparErroTitulo();
}

function limparErroTitulo() {
    const erro = document.getElementById("erroTitulo");
    erro.textContent = "";
    campos.titulo.classList.remove("invalid");
}

function validarFormulario() {
    limparErroTitulo();

    if (campos.titulo.value.trim() === "") {
        const erro = document.getElementById("erroTitulo");
        erro.textContent = "Informe o título da tarefa.";
        campos.titulo.classList.add("invalid");
        campos.titulo.focus();
        return false;
    }

    return true;
}

function abrirNovaTarefa() {
    formTarefa.reset();
    campos.id.value = "";
    document.getElementById("tituloModal").textContent = "Nova tarefa";
    document.getElementById("salvar").textContent = "Salvar tarefa";
    limparErroTitulo();
    abrirModal();
}

// Editar
function abrirEdicao(id) {
    const tarefa = tarefas.find(t => t.id === id);

    if (!tarefa) {
        exibirMensagem("Tarefa não encontrada.", "error");
        return;
    }

    campos.id.value = tarefa.id;
    campos.titulo.value = tarefa.titulo;
    campos.descricao.value = tarefa.descricao || "";
    campos.prioridade.value = tarefa.prioridade;
    campos.status.value = tarefa.status;
    campos.prazo.value = tarefa.prazo || "";

    document.getElementById("tituloModal").textContent = "Editar tarefa";
    document.getElementById("salvar").textContent = "Salvar alterações";

    limparErroTitulo();
    abrirModal();
}

btnSair.addEventListener("click", async evento => {

    evento.preventDefault();

    btnSair.disabled = true;

    try {

        await requisicao(`${API_URL}/logout`, {

            method: "POST",
            credentials: "include"
        });

        window.location.href = "login.html";

    } catch (error) {

        exibirMensagem(erro.message, "error");
        
    }finally {

        btnSair.disabled = false;

    }

})

// Salvar tarefa
formTarefa.addEventListener("submit", async evento => {
    evento.preventDefault();

    if (!validarFormulario()) return;

    const id = campos.id.value;

    const tarefa = {
        titulo: campos.titulo.value.trim(),
        descricao: campos.descricao.value.trim(),
        prioridade: campos.prioridade.value,
        status: campos.status.value,
        prazo: campos.prazo.value || null
    };

    const botaoSalvar = document.getElementById("salvar");
    botaoSalvar.disabled = true;

    try {
        if (id) {
            await requisicao(`${API_URL}/${id}`, {
                method: "PUT",
                credentials: "include",
                body: JSON.stringify(tarefa)
            });

            exibirMensagem("Tarefa atualizada com sucesso.");
        } else {
            await requisicao(API_URL, {
                method: "POST",
                credentials: "include",
                body: JSON.stringify(tarefa)
            });

            exibirMensagem("Tarefa cadastrada com sucesso.");
        }

        fecharModal();
        await carregarTarefas();
    } catch (erro) {
        exibirMensagem(erro.message, "error");
    } finally {
        botaoSalvar.disabled = false;
    }
});

// Excluir
async function excluirTarefa(id) {
    const confirmar = window.confirm(
        "Tem certeza de que deseja excluir esta tarefa?"
    );

    if (!confirmar) return;

    try {
        await requisicao(`${API_URL}/${id}`, {
            method: "DELETE",
            credentials: "include"
        });

        exibirMensagem("Tarefa excluída com sucesso.");
        await carregarTarefas();
    } catch (erro) {
        exibirMensagem(erro.message, "error");
    }
}

// Eventos
document.getElementById("novaTarefa")
    .addEventListener("click", abrirNovaTarefa);

document.getElementById("fecharModal")
    .addEventListener("click", fecharModal);

document.getElementById("cancelar")
    .addEventListener("click", fecharModal);

document.getElementById("atualizarLista")
    .addEventListener("click", carregarTarefas);

busca.addEventListener("input", aplicarFiltros);
filtroStatus.addEventListener("change", aplicarFiltros);
filtroPrioridade.addEventListener("change", aplicarFiltros);

modal.addEventListener("click", evento => {
    if (evento.target === modal) fecharModal();
});

document.addEventListener("keydown", evento => {
    if (evento.key === "Escape" && !modal.hidden) {
        fecharModal();
    }
});

// Inicialização
carregarTarefas();
carregarNome();