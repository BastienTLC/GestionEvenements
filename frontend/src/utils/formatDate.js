export function formatDate(dateString){
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric',
        hour12: false,
        timeZone: 'UTC'
    };
    try {
        return new Date(dateString).toLocaleDateString('fr-FR', options);
    } catch (error) {
        console.error(error);
        return dateString;
    }
}
