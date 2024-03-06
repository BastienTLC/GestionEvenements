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

    return `${heuresString}:${minutesString}`;
}

//fonction qui prend un date sous cette forme : 2024-03-09T23:00:00.000+00:00 et un heure sous cette forme : 23:00 et qui retourne 2024-03-11T23:23:00.000+00:00
export function combineDateAndTime(date, time) {
    const datePart = date.split('T')[0];
    console.log(date, time)

    return new Date(`${datePart}T${time}`);
}
