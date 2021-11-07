package telegram_bot;

import java.util.List;
import java.util.regex.*;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.impl.TelegramBotClient;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import java.net.URL;
import java.net.URLConnection;


@SuppressWarnings("unused")
public class Main {

	public static String strBotName = "JussaFIAP_bot"; //"JussaBot";
	private static String strToken = "";
	//private static String strChatBotToken = "m9FEsl6J3T66j3Au2ZkRCtn2UimobCnl";
	//private static String strChatBotURL = "https://api.chatbot.com";	
	public static boolean gravaNome = false;
	public static boolean bolInicio = true;
	public static String strNomeUser = "";
	//public static URL url = new URL("https://api.chatbot.com/JussaBot");
	//public static String url = "curl --request GET --url https://api.chatbot.com/stories --header 'authorization: Bearer ${" + strChatBotToken + "}'";
	public String getBotUserName() {
		return strBotName;
	}
	
	public static boolean isNumeric(String strNum) {
		try {
			Double.parseDouble(strNum);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static String treatMessage(String strMessage) {
		String strRetorno = "";
		
		//System.out.println("Antes do tratamento");
		//System.out.println(strMessage);
		//strMessage = strMessage.replaceAll("\\W", "");
		//System.out.println("Depois do tratamento");
		//System.out.println(strMessage);
		
		if(strMessage.contains("Oi") || strMessage.contains("Olá") || strMessage.contains("Hello") || strMessage.contains("Bom dia") || strMessage.contains("Boa Tarde")) {
			strRetorno = "Olá Bom dia! Eu sou o " + strBotName + "\nPor favor me diga seu nome?";
			gravaNome = true;			
		}else if (strMessage.contains("Aluno")) {
			strRetorno = "Ok " + strNomeUser + " vamos lá...";
			strRetorno += "\n Você sabia que este Bot foi criado para fins acadêmicos e como trabalho da matéria \"Java Platafom\", não é?";
			strRetorno += "Como Aluno, sua nota não será computada, mas mesmo assim gostaria de saber sua opnião a meu respeito.";
			strRetorno += "Portanto.. que nota daria para mim, de um a dez? Por favor digite somente o número da nota.";
		}else if (strMessage.contains("Professor")) {
			strRetorno = "Ok " + strNomeUser + " vamos lá...";
			strRetorno += "\n Você sabia que este Bot foi criado para fins acadêmicos e como trabalho da matéria \"Java Platafom\", não é?";
			strRetorno += "Como você é um professor, a sua nota será computada e valerá muito saber sua opnião a meu respeito.";
			strRetorno += "Portanto.. que nota daria para mim, de um a dez? Por favor digite somente o número da nota.";
		}else if (strMessage.contains("Não sei") || strMessage.contains("Estou em dúvida") || strMessage.contains("Sei lá")) {
			strRetorno = "Ok vamos lá...";
			strRetorno += "\nPara que eu possa me expressar bem com você, você precisa me explicar corretamente quem é você, ok?";
		}else if (isNumeric(strMessage)) {
			switch (strMessage) {
			case "0":
				strRetorno = "Nossa! ZERO!!?? Que nota horrível! Meu Deus! Vou me esforçar para melhorar!";
				break;
			case "1":
				strRetorno = "Nossa! UM!!?? Que nota horrível! Meu Deus! Vou me esforçar para melhorar!";
				break;
			case "2":
				strRetorno = "Nossa! DOIS!!?? Que nota horrível! Meu Deus! Vou me esforçar para melhorar!";
				break;
			case "3":
				strRetorno = "Nossa! TRES!!?? Que nota horrível! Meu Deus! Vou me esforçar para melhorar!";
				break;
			case "4":
				strRetorno = "Nossa! QUATRO!!?? Onde será que errei tanto!?";
				break;
			case "5":
				strRetorno = "Nota CINCO? Não consegui atingir a média! Preciso me esforçar mais!";
				break;
			case "6":
				strRetorno = "Nota SEIS! Estou quase lá! O que será que faltou para eu atingir a média?";
				break;
			case "7":
				strRetorno = "Nota SETE! Opa.. Em cima da linha! Gostaria de ter feito melhor.";
				break;
			case "8":
				strRetorno = "Nota OITO! Que nota legal! Muito obrigado pela sua avaliação!";
				break;
			case "9":
				strRetorno = "Poxa que legal! Quase um DEZ! Estou muito feliz por esta nota NOVE. Obrigado pela sua avaliação e opnião!";
				break;
			case "10":
				strRetorno = "Uiaaaa!! Maravilha!!! Tirei a nota Máxima!!!! Valeu!! Muito obrigado!!!";
				break;
			}
		} else {
			if(bolInicio) {
				strRetorno = "Olá " + strNomeUser + ", você é Aluno ou Professor no MBA Full Stack Developer da FIAP?";
				bolInicio = false;
			}else if(strNomeUser==""){
				strRetorno = "Olá " + strNomeUser + ", não entendi oq vc quis dizer, podemos começar novamente?";
				strRetorno += "\nPrimeiramente poderia me dizer seu nome?";
				gravaNome = true;
			}else {
				strRetorno = "Olá " + strNomeUser + ", não entendi oq vc quis dizer, podemos começar novamente?";
				strRetorno += "\nVocê é Aluno ou Professor no MBA Full Stack Developer da FIAP?";
			}
		}
		
		return strRetorno;
	}
	
	public static void main(String[] args) {
		
		
		// Criacao do objeto bot com as informacoes de acesso.
		TelegramBot bot = new TelegramBot(strToken);
		
		// Objeto responsavel por receber as mensagens.
		GetUpdatesResponse updatesResponse;

		// Objeto responsavel por gerenciar o envio de respostas.
		SendResponse sendResponse;

		// Objeto responsavel por gerenciar o envio de acoes do chat.
		BaseResponse baseResponse;

		// Controle de off-set, isto e, a partir deste ID sera lido as mensagens
		// pendentes na fila.
		int m = 0;
		String strMessage = "";
		String strResposta = "";
		
		// Loop infinito pode ser alterado por algum timer de intervalo curto.
		while (true) {
			// Executa comando no Telegram para obter as mensagens pendentes a partir de um
			// off-set (limite inicial).
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			// Lista de mensagens.
			List<Update> updates = updatesResponse.updates();

			// Analise de cada acao da mensagem.
			for (Update update : updates) {

				// Atualizacao do off-set.
				m = update.updateId() + 1;

				strMessage = update.message().text();
				if (gravaNome) {
					strNomeUser = strMessage;
					gravaNome = false;
				};
				
				System.out.println(strNomeUser + ": " + strMessage);

				// Envio de "Escrevendo" antes de enviar a resposta.
				baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

				// Verificacao de acao de chat foi enviada com sucesso.
				//System.out.println("Resposta de Chat Action Enviada? " + baseResponse.isOk());
				
				//Tratamento da mensagem antes de enviá-la
				strResposta = telegram_bot.Main.treatMessage(strMessage);
				// Envio da mensagem de resposta.
				sendResponse = bot.execute(new SendMessage(update.message().chat().id(), strResposta));
				System.out.println(strBotName + ": " + strResposta);
				
				
				// Verificacao de mensagem enviada com sucesso.
				//System.out.println("Mensagem Enviada? " + sendResponse.isOk());
			}
		}
	}
}
