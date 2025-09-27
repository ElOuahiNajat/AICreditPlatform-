import os
from flask import Flask, request, jsonify
from google import genai
from google.genai import types
from dotenv import load_dotenv
from flask_cors import CORS  # <-- IMPORTANT

load_dotenv()

app = Flask(__name__)
CORS(app)  # autorise toutes les origines

# Initialisation client
client = genai.Client(api_key=os.environ.get("GEMINI_API_KEY"))
model = "gemini-2.0-flash"


@app.route("/chat", methods=["POST"])
def chat():
    data = request.get_json()
    question = data.get("question")

    if not question:
        return jsonify({"error": "Merci d'envoyer une question"}), 400

    # Construction du contenu
    contents = [
        types.Content(
            role="user",
            parts=[types.Part.from_text(text=question)],
        ),
    ]

    # Configuration
    generate_content_config = types.GenerateContentConfig(
        response_mime_type="text/plain",
    )

    # Réponse du modèle
    response_text = ""
    for chunk in client.models.generate_content_stream(
        model=model,
        contents=contents,
        config=generate_content_config,
    ):
        if chunk.text:
            response_text += chunk.text

    return jsonify({"question": question, "response": response_text})


if __name__ == "__main__":
    app.run(debug=True, port=5000)
