package br.com.amptech.projeto01ws.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.amptech.projeto01ws.domain.Cidade;
import br.com.amptech.projeto01ws.domain.Cliente;
import br.com.amptech.projeto01ws.domain.Endereco;
import br.com.amptech.projeto01ws.domain.enums.TipoCliente;
import br.com.amptech.projeto01ws.dto.ClienteDTO;
import br.com.amptech.projeto01ws.dto.ClienteNewDTO;
import br.com.amptech.projeto01ws.repositories.ClienteRepository;
import br.com.amptech.projeto01ws.repositories.EnderecoRepository;
import br.com.amptech.projeto01ws.security.UserSS;
import br.com.amptech.projeto01ws.services.exceptions.AuthorizationException;
import br.com.amptech.projeto01ws.services.exceptions.DataIntegrityException;
import br.com.amptech.projeto01ws.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;	
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	
	public Cliente find(Integer id) {
					
		Optional<Cliente> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {		
		
		//obj.setId(null);		
		obj = repo.save(obj);
		
		enderecoRepository.saveAll(obj.getEnderecos());		
		
		return obj; 		
	}
	
	public Cliente update(Cliente obj) {		
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());	
	}

	public void delete(Integer id) {
		find(id);
		try {
		  repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos relacionadas.");			
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		Cliente obj = new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
		return obj;
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.ToEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = s3Service.uploadFile(multipartFile);
		
		Cliente cli = find(user.getId());
		cli.setImageUrl(uri.toString());
		repo.save(cli);
				
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		//jpgImage = imageService.cropSquare(jpgImage);
		//jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
	
}
