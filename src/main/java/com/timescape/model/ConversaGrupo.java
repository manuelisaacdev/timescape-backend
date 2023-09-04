package com.timescape.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.timescape.model.converter.TipoConversaConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("conversaGrupo")
@JsonPropertyOrder({"id","nome","tipoConversa","dataCriacao","arquivo","usuario"})
@JsonRootName(value = "conversaGrupo", namespace = "conversasGrupos")
@Table(
	name = "conversas_grupos", 
	indexes = @Index(name = "idx_conversas_grupos_grupo_id", columnList = "grupo_id")
)
public class ConversaGrupo {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 50, nullable = false)
	@Convert(converter = TipoConversaConverter.class)
	private TipoConversa tipoConversa;

	@CreationTimestamp
	@JsonFormat(locale = "AO", shape = Shape.STRING)
	@Column(name = "data_criacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataCriacao;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "arquivo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_conversa_grupo_arquivo"))
	private Arquivo arquivo;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "conversaGrupo", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<MembroConversaGrupo> membros;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "conversaGrupo", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<MensagemConversaGrupo> mensagens;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "grupo_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_conversa_grupo_grupo"))
	private Grupo grupo;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_conversa_grupo_usuario"))
	private Usuario usuario;

}
