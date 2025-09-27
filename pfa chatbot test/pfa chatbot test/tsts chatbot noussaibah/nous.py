import os
from google import genai
from google.genai import types
from dotenv import load_dotenv

load_dotenv()

def main():
    client = genai.Client(api_key=os.environ.get("GEMINI_API_KEY"))
    model = "gemini-2.0-flash"

    print("=== Chat avec Gemini (tape 'exit' pour quitter) ===")

    while True:
        # Demande de la question à l’utilisateur
        question = input("\n👉 Toi: ")
        if question.lower() in ["exit", "quit", "q"]:
            print("👋 Fin de la session.")
            break

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

        # Réponse du modèle (streamée)
        print("🤖 Gemini:", end=" ", flush=True)
        for chunk in client.models.generate_content_stream(
            model=model,
            contents=contents,
            config=generate_content_config,
        ):
            if chunk.text:
                print(chunk.text, end="", flush=True)
        print()  # saut de ligne après la réponse

if __name__ == "__main__":
    main()
