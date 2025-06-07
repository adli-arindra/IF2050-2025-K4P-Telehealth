// Impor React dan useState untuk mengelola state form
import React, { useState } from 'react';

const MedicalRecordForm = ({ onSubmit }) => {
  // State untuk menyimpan data inputan form
  const [formData, setFormData] = useState({
    patientId: '',       // ID pasien (diinput manual)
    diagnosis: '',       // Diagnosis penyakit
    treatment: '',       // Tindakan pengobatan
    recordDate: ''       // Tanggal pemeriksaan
  });

  // Fungsi untuk menangani perubahan input
  const handleChange = (e) => {
    const { name, value } = e.target;

    // Update state sesuai field yang diubah
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  // Fungsi untuk meng-handle submit form
  const handleSubmit = (e) => {
    e.preventDefault(); // Mencegah reload halaman

    // Kirim data form ke fungsi yang diberikan melalui props
    onSubmit(formData);

    // Reset isi form setelah submit
    setFormData({
      patientId: '',
      diagnosis: '',
      treatment: '',
      recordDate: ''
    });
  };

  return (
    // Tag <form> untuk menangkap input pengguna
    <form onSubmit={handleSubmit} className="p-4 bg-white rounded shadow-md max-w-md mx-auto">
      <h2 className="text-xl font-bold mb-4">Form Rekam Medis</h2>

      {/* Input ID Pasien */}
      <label className="block mb-2">
        ID Pasien:
        <input
          type="text"
          name="patientId"
          value={formData.patientId}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Input Diagnosis */}
      <label className="block mb-2">
        Diagnosis:
        <textarea
          name="diagnosis"
          value={formData.diagnosis}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Input Tindakan / Treatment */}
      <label className="block mb-2">
        Tindakan Pengobatan:
        <textarea
          name="treatment"
          value={formData.treatment}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Input Tanggal Rekam Medis */}
      <label className="block mb-4">
        Tanggal Pemeriksaan:
        <input
          type="date"
          name="recordDate"
          value={formData.recordDate}
          onChange={handleChange}
          className="w-full border border-gray-300 rounded p-2"
          required
        />
      </label>

      {/* Tombol Submit */}
      <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
        Simpan Rekam Medis
      </button>
    </form>
  );
};

export default MedicalRecordForm;