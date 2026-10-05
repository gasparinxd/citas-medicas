import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [citas, setCitas] = useState([])
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('/api/citas')
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`)
        return res.json()
      })
      .then(setCitas)
      .catch((err) => setError(err.message))
  }, [])

  return (
    <main>
      <h1>Citas Médicas</h1>
      {error && <p className="error">No se pudo conectar con el backend: {error}</p>}
      {!error && citas.length === 0 && <p>No hay citas agendadas.</p>}
      <ul>
        {citas.map((c) => (
          <li key={c.id}>
            #{c.id} — {c.fechaHora} — paciente {c.pacienteId}, médico {c.medicoId}
          </li>
        ))}
      </ul>
    </main>
  )
}

export default App
