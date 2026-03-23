async function fetchTemperature() {
    try {
        const response = await fetch('/weather');
        const data = await response.json();
        
        if (data.temperature !== undefined) {
			const temp = parseFloat(data.temperature).toFixed(1);
            document.getElementById('temp-value').textContent = temp;
        } else {
            document.getElementById('temp-value').textContent = 'Error';
        }
    } catch (error) {
        console.error("Fetch error:", error);
        document.getElementById('temp-value').textContent = 'Error';
    }
}


//window.onload = fetchTemperature;
