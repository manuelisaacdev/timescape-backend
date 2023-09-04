package com.timescape.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("Conversa Grupo")
@JsonClassDescription("mensagemConversaGrupo")
@JsonPropertyOrder({"id","texto","tipo","dataMensagem","arquivos","usuario","reacoes"})
@JsonRootName(value = "mensagemConversaGrupo", namespace = "mensagensConversasGrupos")
@PrimaryKeyJoinColumn(name="id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_mensagem_conversa_grupo_mensagem"))
@Table(
	name = "mensagens_conversas_grupos", 
	indexes = @Index(name = "idx_mensagens_conversas_grupos_conversa_grupo_id", columnList = "conversa_grupo_id")
)
public class MensagemConversaGrupo extends Mensagem {

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "conversa_grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_mensagem_conversa_grupo_conversa_grupo"))
	private ConversaGrupo conversaGrupo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "membro_conversa_grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_mensagem_conversa_grupo_membro_conversa_grupo"))
	private MembroConversaGrupo membro;

}