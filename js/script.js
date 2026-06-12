
// Instância global do mapa Leaflet
let map; 
// Array de marcadores ativos no mapa
let markers = []; 


function initMap() {
    if (!document.getElementById('map')) return;

    const maringaCentro = [-23.4251, -51.9386];
    map = L.map('map').setView(maringaCentro, 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    // Busca os pontos do back-end via API REST
    fetch('http://localhost:8080/api/pontos-descarte')
        .then(response => response.json())
        .then(pontos => {
            pontos.forEach(ponto => {
                if (ponto.latitude !== null && ponto.longitude !== null) {
                    addMarker(
                        ponto.latitude,
                        ponto.longitude,
                        ponto.nome,
                        `${ponto.residuosAceitos}. Endereço: ${ponto.endereco}`
                    );
                }
            });
        })
        .catch(error => {
            console.error('Erro ao carregar pontos de descarte:', error);
            alert('⚠️ Não foi possível carregar os pontos de descarte. Certifique-se de que o Back-end está rodando.');
        });

    getCurrentLocation();
}

/**
 * Adiciona um marcador no mapa com popup de título e informações.
 * @param {number} lat - Latitude do ponto
 * @param {number} lng - Longitude do ponto
 * @param {string} title - Nome do ponto de descarte
 * @param {string} info - Informações adicionais (resíduos aceitos e endereço)
 */
function addMarker(lat, lng, title, info) {
    if (!map) return;
    const marker = L.marker([lat, lng]).addTo(map);
    marker.bindPopup(`<h3>${title}</h3><p>${info}</p>`);
    markers.push(marker);
}

/**
 * Busca um endereço digitado pelo usuário via API Nominatim
 * e centraliza o mapa na localização encontrada.
 */
function searchAddress() {
    if (!map) return;
    const address = document.getElementById("search-address").value;
    if (!address) {
        alert("Digite um endereço.");
        return;
    }
    fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(address)}&countrycodes=br&addressdetails=1`)
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                const lat = data[0].lat;
                const lon = data[0].lon;
                map.setView([lat, lon], 15);
                // Remove marcador de busca anterior, se existir
                markers = markers.filter(marker => {
                    if (marker.getPopup().getContent().includes("Localização Encontrada")) {
                        map.removeLayer(marker);
                        return false;
                    }
                    return true;
                });
                const newMarker = L.marker([lat, lon]).addTo(map);
                newMarker.bindPopup(`<h3>Localização Encontrada</h3><p>${data[0].display_name}</p>`).openPopup();
                markers.push(newMarker);
            } else {
                alert("Endereço não encontrado.");
            }
        })
        .catch(() => {
            alert("Erro ao buscar o endereço. Verifique sua conexão ou tente novamente.");
        });
}

/**
 * Obtém a localização atual do usuário via GPS do dispositivo
 * e centraliza o mapa com um marcador personalizado.
 */
function getCurrentLocation() {
    if (!map) return;
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const lat = position.coords.latitude;
                const lng = position.coords.longitude;
                map.setView([lat, lng], 15);

                // Ícone personalizado para localização do usuário
                const userIcon = L.icon({
                    iconUrl: "../ECO/imagem/red.png",
                    iconSize: [35, 35],
                    iconAnchor: [17, 35],
                    popupAnchor: [0, -35],
                    shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
                    shadowSize: [41, 41]
                });

                // Remove marcador de localização anterior, se existir
                markers = markers.filter(marker => {
                    if (marker.getPopup().getContent().includes("Sua Localização")) {
                        map.removeLayer(marker);
                        return false;
                    }
                    return true;
                });
                const newMarker = L.marker([lat, lng], { icon: userIcon }).addTo(map);
                newMarker.bindPopup("<h3>Sua Localização</h3>").openPopup();
                markers.push(newMarker);
            },
            (error) => {
                console.error("Erro ao obter localização: ", error);
            },
            { enableHighAccuracy: true, timeout: 10000, maximumAge: 0 }
        );
    }
}

/**
 * Calcula o impacto ambiental estimado com base na quantidade
 * de equipamentos eletrônicos descartados pelo usuário.
 * Exibe o resultado em kg de CO2 evitado.
 */
window.calcularImpacto = function() {
    const qtdComputadores = parseInt(document.getElementById('qtd-computadores').value) || 0;
    const qtdCelulares = parseInt(document.getElementById('qtd-celulares').value) || 0;
    const qtdTelevisoes = parseInt(document.getElementById('qtd-televisoes').value) || 0;
    const resultadoImpactoDiv = document.getElementById('resultado-impacto');

    // Impacto médio estimado em kg de CO2 por equipamento
    const impactoComputador = 20; 
    const impactoCelular = 10;
    const impactoTelevisao = 25;

    const impactoTotal = (qtdComputadores * impactoComputador) +
                         (qtdCelulares * impactoCelular) +
                         (qtdTelevisoes * impactoTelevisao);

    if (resultadoImpactoDiv) {
        resultadoImpactoDiv.innerHTML = `Seu descarte consciente pode evitar a emissão de aproximadamente ${impactoTotal} kg de CO2 e conservar recursos naturais. Obrigado!`;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const menuToggle = document.querySelector('.menu-toggle');
    const menu = document.querySelector('.menu'); 
    const body = document.body;

    // Lógica do Menu Mobile
    if (menuToggle && menu) {
        menuToggle.addEventListener('click', () => {
            const isActive = menu.classList.toggle('active');
            menuToggle.classList.toggle('active');
            menuToggle.setAttribute('aria-expanded', isActive ? 'true' : 'false');
            body.classList.toggle('no-scroll', isActive);
        });

        // Fecha o menu ao clicar em qualquer link
        const menuLinks = menu.querySelectorAll('a');
        menuLinks.forEach(link => {
            link.addEventListener('click', () => {
                if (menu.classList.contains('active')) {
                    menu.classList.remove('active');
                    menuToggle.classList.remove('active');
                    menuToggle.setAttribute('aria-expanded', 'false');
                    body.classList.remove('no-scroll');
                }
            });
        });
    }

    // Lógica de envio do formulário de agendamento
    const formAgendamento = document.getElementById('form-agendamento');
    if (formAgendamento) {
        formAgendamento.addEventListener('submit', function(event) {
            event.preventDefault(); // Impede o envio tradicional do formulário

            // Coleta dos dados do formulário
            const nome = document.getElementById('nome').value;
            const email = document.getElementById('email').value;
            const telefone = document.getElementById('telefone').value;
            const tipoResiduo = document.getElementById('tipo-residuo').value;
            const endereco = document.getElementById('endereco').value;
            const dataColeta = document.getElementById('data-coleta').value;
            const observacoes = document.getElementById('observacoes').value;

            // Validação dos campos obrigatórios
            if (!nome || !email || !tipoResiduo || !endereco || !dataColeta) {
                alert('Por favor, preencha todos os campos obrigatórios.');
                return;
            }

            // Monta o objeto JSON para envio ao back-end
            const dadosAgendamento = {
                nome: nome,
                email: email,
                telefone: telefone,
                tipoResiduo: tipoResiduo,
                endereco: endereco,
                dataColeta: dataColeta, // Formato AAAA-MM-DD
                observacoes: observacoes
            };

            // Envia os dados para a API REST do Spring Boot
            fetch('http://localhost:8080/api/agendamentos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dadosAgendamento)
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                return response.text().then(errorText => {
                    throw new Error(`Erro ${response.status}: ${errorText || response.statusText}`);
                });
            })
            .then(data => {
                console.log('Agendamento salvo com sucesso. ID:', data.id);
                alert('✅ Agendamento solicitado com sucesso! Seu ID de agendamento é: ' + data.id);
                formAgendamento.reset(); // Limpa o formulário após sucesso
            })
            .catch(error => {
                console.error('Erro ao agendar:', error);
                alert('❌ Ocorreu um erro ao tentar agendar. Certifique-se de que o Back-end Spring Boot está rodando.');
            });
        });
    }

    // Inicialização do Mapa
    if (document.getElementById('map')) {
        initMap();
    }
});