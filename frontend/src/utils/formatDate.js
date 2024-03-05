export function formatDate(dateString){
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
    };
    try {
        return new Date(dateString).toLocaleDateString('fr-FR', options);
    } catch (error) {
        console.error(error);
        return dateString;
    }
}


export function formatDuree(duree) {
    const secondes = Math.floor(duree / 1000);

    const heures = Math.floor(secondes / 3600);
    const minutes = Math.floor((secondes % 3600) / 60);
    const secondesRestantes = secondes % 60;

    const heuresString = heures < 10 ? `0${heures}` : heures.toString();
    const minutesString = minutes < 10 ? `0${minutes}` : minutes.toString();
    const secondesString = secondesRestantes < 10 ? `0${secondesRestantes}` : secondesRestantes.toString();

    return `${heuresString}:${minutesString}:${secondesString}`;
}
