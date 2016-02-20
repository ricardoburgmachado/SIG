/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoDadosGeotecnicos;

import Modelo.GerenciamentoUsuarios.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.stereotype.Component;

@Entity
@Table(name="ilha")
public class Ilha extends Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ILHA")
    private int id;
    
    @Basic(optional = false)
    @Column(name = "NOME_ILHA")
    private String nome;
    
    @Basic(optional = false)
    @Column(name = "LOCAL_ILHA")
    private String local;

    @Basic(optional = true)
    @Column(name = "ENDERECO_ILHA")
    private String endereco;

    @Basic(optional = true)
    @Column(name = "AUTOR_ILHA")
    private String autor;
 
    @Basic(optional = false)
    @Column(name = "REFERENCIA_ILHA")
    private String referencia;
    
    @Basic(optional = false)
    @Column(name = "LATITUDE_ILHA")
    private Double latitude;

    @Basic(optional = false)
    @Column(name = "LONGITUDE_ILHA")
    private Double longitude;
    
    @Basic(optional = true)
    @Column(name = "DATA_EXECUCAO_ILHA")
    private Date dataExecucao;

    @Transient // tem que prestar atenção nessa assinatura para ver se não dará problemas no registro de uma ilha
    @OneToMany(mappedBy="ilha")        
    private ArrayList<Ensaio> ensaios;

    @Basic(optional = false)
    @Column(name = "ENSAIOS_REALIZADOS_ILHA")    
    private String ensaiosRealizados;    

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="FK_ID_USUARIO", referencedColumnName="ID_USUARIO")
    private Usuario usuario;
    
    public Ilha(){
        this.ensaios = new ArrayList<>();
    }
    
    public Ilha(int idIlha){
        this.id = idIlha;
        this.ensaios = new ArrayList<>();
    }
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
    
    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * @return the local
     */
    public String getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the ensaiosRealizados
     */
    public String getEnsaiosRealizados() {
        return ensaiosRealizados;
    }

    /**
     * @param ensaiosRealizados the ensaiosRealizados to set
     */
    public void setEnsaiosRealizados(String ensaiosRealizados) {
        this.ensaiosRealizados = ensaiosRealizados;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the dataExecucao
     */
    public Date getDataExecucao() {
        return dataExecucao;
    }

    /**
     * @param dataExecucao the dataExecucao to set
     */
    public void setDataExecucao(Date dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    /**
     * @return the ensaios
     */
    public List<Ensaio> getEnsaios() {
        return this.ensaios;
    }

    /**
     * @param ensaios the ensaios to set
     */
    public void setEnsaios(ArrayList<Ensaio> ensaios) {
        this.ensaios = ensaios;
    }
    
    public void setEnsaio(Ensaio ensaio) {
        this.ensaios.add(ensaio);
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
