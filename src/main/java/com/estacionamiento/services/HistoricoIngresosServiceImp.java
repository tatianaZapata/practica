package com.estacionamiento.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estacionamiento.models.HistoricoIngresos;
import com.estacionamiento.repositories.HistoricoIngresosRepository;

@Service
@Transactional
public class HistoricoIngresosServiceImp implements HistoricoIngresosService{
	
	@Autowired
	private HistoricoIngresosRepository historicoIngresosRepository;

	@Override
	public HistoricoIngresos guardarHistorico(HistoricoIngresos historico) throws Exception {
		return historicoIngresosRepository.save(historico);
	}

	@Override
	public Optional<HistoricoIngresos> consultarHistorico(Integer id) throws Exception {
		return historicoIngresosRepository.findById(id);
	}

	@Override
	public HistoricoIngresos modificarHistorico(HistoricoIngresos historico) throws Exception {
		return historicoIngresosRepository.save(historico);
	}

	@Override
	public void eliminarHistorico(Integer id) throws Exception {
		historicoIngresosRepository.deleteById(id);
	}

	@Override
	public List<HistoricoIngresos> listarHistorico() throws Exception {
		return historicoIngresosRepository.findAll();
	}
	
	
}
