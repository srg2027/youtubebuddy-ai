async function summarizeVideo() {

    const youtubeUrl = document.getElementById("youtubeUrl").value;
    const loading = document.getElementById("loading");
    const summary = document.getElementById("summary");

    loading.innerText = "Generating Summary...";
    summary.innerText = "";

    try {

        const response = await fetch(
            "/api/video/summary",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    youtubeUrl: youtubeUrl
                })
            }
        );

        const data = await response.json();

        summary.innerText = data.summary;

    } catch (error) {

        summary.innerText = "Something went wrong.";
        console.error(error);

    }

    loading.innerText = "";
}

async function generateKeyPoints() {

    const youtubeUrl = document.getElementById("youtubeUrl").value;
    const loading = document.getElementById("loading");
    const summary = document.getElementById("summary");

    loading.innerText = "Generating Key Points...";
    summary.innerText = "";

    try {

        const response = await fetch(
            "/api/video/keypoints",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    youtubeUrl: youtubeUrl
                })
            }
        );

        const data = await response.json();

        summary.innerText = data.keyPoints;

    } catch (error) {

        summary.innerText = "Something went wrong.";
        console.error(error);

    }

    loading.innerText = "";
}

async function generateTitles() {

    const youtubeUrl = document.getElementById("youtubeUrl").value;
    const loading = document.getElementById("loading");
    const summary = document.getElementById("summary");

    loading.innerText = "Generating Titles...";
    summary.innerText = "";

    try {

        const response = await fetch(
            "/api/video/titles",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    youtubeUrl: youtubeUrl
                })
            }
        );

        const data = await response.json();

        summary.innerText = data.titles;

    } catch (error) {

        summary.innerText = "Something went wrong.";
        console.error(error);

    }

    loading.innerText = "";
}

async function generateDescription() {

    const youtubeUrl =
        document.getElementById("youtubeUrl").value;

    const loading =
        document.getElementById("loading");

    const summary =
        document.getElementById("summary");

    loading.innerText = "Generating Description...";
    summary.innerText = "";

    try {

        const response = await fetch(
            "/api/video/description",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    youtubeUrl: youtubeUrl
                })
            }
        );

        const data = await response.json();

        summary.innerText =
            data.description;

    } catch (error) {

        summary.innerText =
            "Something went wrong.";

    }

    loading.innerText = "";
}
async function generateTags() {

    const youtubeUrl =
        document.getElementById("youtubeUrl").value;

    const loading =
        document.getElementById("loading");

    const summary =
        document.getElementById("summary");

    loading.innerText = "Generating Tags...";
    summary.innerText = "";

    try {

        const response = await fetch(
            "/api/video/tags",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    youtubeUrl: youtubeUrl
                })
            }
        );

        const data = await response.json();

        summary.innerText =
            data.tags;

    } catch (error) {

        summary.innerText =
            "Something went wrong.";

    }

    loading.innerText = "";
}

async function generateQuiz() {

    const youtubeUrl =
        document.getElementById("youtubeUrl").value;

    const loading =
        document.getElementById("loading");

    const summary =
        document.getElementById("summary");

    loading.innerText = "Generating Quiz...";
    summary.innerText = "";

    try {

        const response = await fetch(
            "/api/video/quiz",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    youtubeUrl: youtubeUrl
                })
            }
        );

        const data = await response.json();

        summary.innerText = data.quiz;

    } catch (error) {

        summary.innerText =
            "Something went wrong.";

        console.error(error);
    }

    loading.innerText = "";
}